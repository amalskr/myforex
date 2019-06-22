#include <jni.h>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_ceylonapz_myforex_util_Constants_getBaseUrl(JNIEnv *env, jclass type) {
    return env->NewStringUTF("https://api.exchangeratesapi.io");
}