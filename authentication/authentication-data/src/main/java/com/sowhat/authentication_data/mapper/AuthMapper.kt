package com.sowhat.authentication_data.mapper

import com.sowhat.authentication_data.model.response.NewMemberInfo
import com.sowhat.authentication_data.model.response.SignInResult
import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.model.SignIn

fun NewMemberInfo.toNewMember() = NewMember(
    accessToken = accessToken,
    memberId = memberId
)

fun SignInResult.toSignIn() = SignIn(
    accessToken = accessToken,
    isJoined = isJoined,
    memberId = memberId
)