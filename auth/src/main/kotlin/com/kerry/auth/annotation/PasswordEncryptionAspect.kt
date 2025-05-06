package com.kerry.auth.annotation

import com.kerry.auth.util.PasswordEncryptor
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class PasswordEncryptionAspect(
    private val passwordEncryptor: PasswordEncryptor
) {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    fun encryptAnnotatedFields(joinPoint: ProceedingJoinPoint): Any? {
        joinPoint.args.forEach { arg ->
            if (arg == null) return@forEach

            arg::class.java.declaredFields
                .filter { it.isAnnotationPresent(PasswordEncryption::class.java) }
                .forEach { field ->
                    field.isAccessible = true
                    val raw = field.get(arg) as? String ?: return@forEach

                    val encrypted = passwordEncryptor.encryptPassword(raw)
                    field.set(arg, encrypted)
                }
        }

        return joinPoint.proceed()
    }
}