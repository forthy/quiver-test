package org.example.richard.demo.models

sealed interface DomainError

data class InvalidFirstName(val invalidName: String) : DomainError

data class InvalidLastName(val invalidName: String) : DomainError

data class InvalidMiddleName(val invalidName: String) : DomainError
