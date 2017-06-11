#include <jni.h>
#include <string>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_liupei_bandwagon_UrlConfig_getVeid(JNIEnv *env, jobject) {
    std::string veid = "545832";
    return env->NewStringUTF(veid.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_liupei_bandwagon_UrlConfig_getApiKey(JNIEnv *env, jobject) {
    std::string apikey = "private_OAbba9x6qidloiodYlM0kH6V";
    return env->NewStringUTF(apikey.c_str());
}
}


