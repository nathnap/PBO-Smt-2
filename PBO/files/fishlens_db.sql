-- ================================================================
-- FishLens Database Schema
-- Jalankan script ini di phpMyAdmin atau MySQL Workbench
-- sebelum menjalankan program Java
-- ================================================================

CREATE DATABASE IF NOT EXISTS fishlens_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE fishlens_db;

-- ── Tabel Users (Penghobi + Mentor) ──────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    nama                VARCHAR(100)  NOT NULL,
    email               VARCHAR(100)  NOT NULL UNIQUE,
    password            VARCHAR(100)  NOT NULL,
    role                ENUM('PENGHOBI','MENTOR') NOT NULL DEFAULT 'PENGHOBI',
    -- Kolom khusus PENGHOBI
    hobi_ikan           VARCHAR(100)  DEFAULT '-',
    tingkat_keanggotaan ENUM('PEMULA','MENENGAH','AHLI') DEFAULT 'PEMULA',
    tanggal_daftar      DATE          DEFAULT (CURRENT_DATE),
    -- Kolom khusus MENTOR
    keahlian            VARCHAR(100)  DEFAULT NULL,
    status_verifikasi   BOOLEAN       DEFAULT FALSE,
    total_sesi          INT           DEFAULT 0,
    -- Timestamps
    created_at          TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ── Tabel Ikan ────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS ikan (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nama_spesies    VARCHAR(150) NOT NULL,
    habitat         VARCHAR(200) DEFAULT '-',
    pakan           VARCHAR(200) DEFAULT '-',
    penyakit_umum   VARCHAR(200) DEFAULT '-',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ── Tabel Postingan (Forum) ───────────────────────────────────────
CREATE TABLE IF NOT EXISTS postingan (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    judul       VARCHAR(200) NOT NULL,
    isi         TEXT         NOT NULL,
    user_id     INT          NOT NULL,
    tanggal     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ── Tabel Artikel ─────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS artikel (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    judul       VARCHAR(200) NOT NULL,
    isi         TEXT         NOT NULL,
    mentor_id   INT          NOT NULL,
    status      ENUM('PENDING','APPROVED') DEFAULT 'PENDING',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ================================================================
-- DATA SAMPLE (opsional, untuk testing)
-- ================================================================

-- User Penghobi
INSERT INTO users (nama, email, password, role, hobi_ikan, tingkat_keanggotaan, tanggal_daftar)
VALUES ('Nelson Hobi', 'nelson@test.com', '12345', 'PENGHOBI', 'Cupang Hias', 'PEMULA', CURRENT_DATE);

-- User Mentor
INSERT INTO users (nama, email, password, role, keahlian, status_verifikasi)
VALUES ('Dr. Mentor', 'mentor@test.com', '12345', 'MENTOR', 'Ikan Air Tawar', TRUE);

-- Data Ikan
INSERT INTO ikan (nama_spesies, habitat, pakan, penyakit_umum) VALUES
('Cupang Betta', 'Air tawar, kolam dangkal', 'Jentik nyamuk, cacing sutera', 'Fin Rot, Ich'),
('Mas Koki', 'Air tawar, akuarium', 'Pellet, sayuran rebus', 'Dropsy, White Spot'),
('Neon Tetra', 'Air tawar tropis', 'Artemia, pelet mini', 'Neon Tetra Disease'),
('Arwana Silver', 'Sungai besar, rawa', 'Ikan kecil, udang', 'Eye Drop Disease'),
('Koi', 'Kolam outdoor, sungai', 'Pellet koi, roti, sayuran', 'Koi Herpesvirus');

-- Artikel (dengan status APPROVED agar langsung tampil)
INSERT INTO artikel (judul, isi, mentor_id, status) VALUES
('Cara Merawat Cupang Hias', 'Cupang adalah ikan yang mudah dirawat. Ganti air 30% setiap 3 hari...', 2, 'APPROVED'),
('Penyakit Umum Ikan Hias', 'White spot atau Ich adalah penyakit paling umum. Tanda-tandanya...', 2, 'APPROVED');

-- Postingan Forum
INSERT INTO postingan (judul, isi, user_id) VALUES
('Tips cupang tidak mau makan?', 'Cupang saya 3 hari tidak mau makan, ada yang tahu solusinya?', 1),
('Rekomendasi filter akuarium 60cm', 'Mau beli filter untuk akuarium 60cm, ada rekomendasi yang bagus?', 1);
