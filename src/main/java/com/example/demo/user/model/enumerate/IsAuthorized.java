package com.example.demo.user.model.enumerate;

public enum IsAuthorized {
    /**
     * 교사가 권한을 획득했는지의 여부
     * 교사가 교사 전용 회원가입 성공 시, isAuthorized는 NO로 설정됨
     * 관리자가 교사의 자격을 증명할 수 있는 자료를 검토 후, isAuthorized를 YES로 설정 -> todo
     */
    YES, NO
}
