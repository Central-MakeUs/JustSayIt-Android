package com.sowhat.network.common

enum class ResponseCode(val responseResult: HttpStatus, val code: String, val message: String) {
    // 2000 - 성공
    OK(HttpStatus.OK,"2000", "성공"),

    // 1000 - SECURITY
    UNAUTHENTICATED(HttpStatus.ERROR, "1000", "인증되지 않은 사용자입니다."),
    JWT_TOKEN_EXPIRED(HttpStatus.ERROR, "1001", "jwt 토큰이 파기되었습니다."),
    NO_ACCESS_PERMISSION(HttpStatus.ERROR, "1002", "접근 권한이 없습니다."),

    // 3000 - MEMBER
    NO_MEMBER(HttpStatus.ERROR, "3000", "회원이 존재하지 않습니다."),
    ALREADY_EXISTS_MEMBER(HttpStatus.ERROR, "3001", "이미 가입한 회원입니다."),
    INVALID_NICKNAME_LENGTH(HttpStatus.ERROR, "3002", "닉네임은 2글자 이상, 12글자 이하여야 합니다."),

    // 4000 - S3
    FILE_SIZE_OVERFLOW(HttpStatus.ERROR, "4000", "개별 사진 사이즈는 최대 10MB, 총합 사이즈는 최대 100MB를 초과할 수 없습니다."),
    FAIL_TO_UPLOAD_FILE(HttpStatus.ERROR, "4001", "AWS 서비스가 원활하지 않아 사진 업로드에 실패했습니다.");

    companion object {
        fun getErrorMessageByCode(code: String): String {
            return ResponseCode.values().find { code == it.code }?.message ?: "예상치 못한 에러가 발생하였습니다."
        }
    }
}

enum class HttpStatus {
    OK, ERROR
}