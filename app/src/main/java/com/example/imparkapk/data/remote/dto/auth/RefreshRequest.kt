package com.example.imparkapk.data.remote.dto.auth

import kotlinx.coroutines.flow.Flow

data class RefreshRequest(val refreshToken: Flow<String?>)
