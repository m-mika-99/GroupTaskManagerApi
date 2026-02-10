package com.example.GroupTaskManagerApi.domain.auth.model;

/**
 * ハッシュ化パスワード(値)
 *
 * @param value ハッシュ値
 */
public record PasswordHash(String value) {
    // TODO 検証の上作成するstatic methodを実装
}
