package com.ComoPaPobre.main.security;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 3;
    private final long BLOCK_DURATION = 10; 

    private final ConcurrentHashMap<String, Integer> attempts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> blockedUntil = new ConcurrentHashMap<>();

    public void loginSucceeded(String key) {
        attempts.remove(key);
        blockedUntil.remove(key);
    }

    public void loginFailed(String key) {
        int currentAttempts = attempts.getOrDefault(key, 0);
        attempts.put(key, currentAttempts + 1);
        if (currentAttempts + 1 >= MAX_ATTEMPTS) {
            blockedUntil.put(key, LocalDateTime.now().plusSeconds(BLOCK_DURATION));
        }
    }

    public boolean isBlocked(String key) {
        if (!blockedUntil.containsKey(key)) return false;
        LocalDateTime until = blockedUntil.get(key);
        if (LocalDateTime.now().isAfter(until)) {
            blockedUntil.remove(key);
            attempts.remove(key);
            return false;
        }
        return true;
    }
}
