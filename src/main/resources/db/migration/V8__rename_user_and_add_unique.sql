ALTER TABLE users
        RENAME COLUMN user TO username,
        ADD CONSTRAINT uk_users_username UNIQUE (username);