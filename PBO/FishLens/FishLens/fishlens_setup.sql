-- ============================================================
-- FishLens — SQL Setup Script
-- Jalankan di MySQL Workbench / phpMyAdmin / CLI sebelum run program
-- ============================================================

-- Buat database
CREATE DATABASE IF NOT EXISTS fishlens
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE fishlens;

-- ============================================================
-- Tabel users — menyimpan Penghobi dan Mentor
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    nama                VARCHAR(100)  NOT NULL,
    email               VARCHAR(150)  NOT NULL UNIQUE,
    password            VARCHAR(255)  NOT NULL,
    role                ENUM('PENGHOBI','MENTOR') NOT NULL,

    -- Kolom khusus PENGHOBI
    hobi_ikan           VARCHAR(100)  DEFAULT NULL,
    tingkat_keanggotaan VARCHAR(20)   DEFAULT 'PEMULA',
    tanggal_daftar      DATE          DEFAULT (CURDATE()),

    -- Kolom khusus MENTOR
    keahlian            VARCHAR(100)  DEFAULT NULL,
    status_verifikasi   TINYINT(1)    DEFAULT 0,
    total_sesi          INT           DEFAULT 0,

    created_at          TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Tabel ikan — ensiklopedia ikan hias
-- ============================================================
CREATE TABLE IF NOT EXISTS ikan (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nama_spesies    VARCHAR(150) NOT NULL,
    habitat         TEXT,
    pakan           TEXT,
    penyakit_umum   TEXT,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Tabel postingan — forum diskusi
-- ============================================================
CREATE TABLE IF NOT EXISTS postingan (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    judul       VARCHAR(200) NOT NULL,
    isi         TEXT         NOT NULL,
    user_id     INT          NOT NULL,
    tanggal     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- Tabel artikel — artikel edukasi dari mentor
-- ============================================================
CREATE TABLE IF NOT EXISTS artikel (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    judul       VARCHAR(200) NOT NULL,
    isi         TEXT         NOT NULL,
    mentor_id   INT          NOT NULL,
    status      ENUM('PENDING','APPROVED') DEFAULT 'PENDING',
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- DATA SAMPLE
-- ============================================================

-- User: 2 Penghobi + 2 Mentor
INSERT INTO users (nama, email, password, role, hobi_ikan, tingkat_keanggotaan, tanggal_daftar) VALUES
('Nelson Wijaya',  'nelson@email.com', 'pass123', 'PENGHOBI', 'Cupang Hias',  'PEMULA',    '2025-01-10'),
('Sari Dewi',      'sari@email.com',   'pass123', 'PENGHOBI', 'Ikan Koi',    'MENENGAH',  '2025-02-15');

INSERT INTO users (nama, email, password, role, keahlian, status_verifikasi, total_sesi) VALUES
('Dr. Rizky',  'rizky@email.com',  'mentor123', 'MENTOR', 'Penyakit Ikan Hias', 1, 12),
('Ibu Wati',   'wati@email.com',   'mentor123', 'MENTOR', 'Nutrisi & Pakan',    1,  8);

-- Ikan sample
INSERT INTO ikan (nama_spesies, habitat, pakan, penyakit_umum) VALUES
('Betta splendens',      'Air tawar, tropis Asia Tenggara',    'Cacing sutra, pelet khusus, serangga',     'White spot, velvet, fin rot'),
('Carassius auratus',    'Air tawar, toleran suhu dingin',     'Pelet, sayuran rebus, cacing',              'Ich, bacterial infections'),
('Pterophyllum scalare', 'Sungai Amazon, air lunak',           'Artemia, bloodworm, pelet',                 'Hexamita, hole-in-the-head'),
('Poecilia reticulata',  'Air tawar hangat, toleran kondisi',  'Pelet kecil, alga, cacing mikro',           'Fin rot, velvet'),
('Symphysodon discus',   'Sungai Amazon, air hangat asam',     'Artemia, cacing darah, pelet premium',      'Gill flukes, blackworm parasit');

-- Artikel sample
INSERT INTO artikel (judul, isi, mentor_id, status) VALUES
('Panduan Lengkap Merawat Ikan Cupang',
 'Ikan cupang (Betta splendens) adalah ikan hias air tawar yang populer. Perawatan meliputi pergantian air 25-30% per minggu, pemberian pakan 2x sehari, dan pencahayaan 8-10 jam per hari. Hindari menempatkan dua jantan dalam satu wadah.',
 3, 'APPROVED'),

('Nutrisi Optimal untuk Ikan Hias Tropis',
 'Nutrisi seimbang adalah kunci kesehatan ikan. Berikan variasi pakan: pelet komersial sebagai pakan utama, cacing sutra/bloodworm 2-3x seminggu sebagai suplemen protein, dan sesekali sayuran rebus untuk herbivora.',
 4, 'APPROVED'),

('Mengatasi Penyakit White Spot (Ich)',
 'White spot disebabkan parasit Ichthyophthirius multifiliis. Gejala: bintik putih di seluruh tubuh dan sirip. Penanganan: naikkan suhu air ke 28-30°C selama 10 hari, tambahkan obat anti-parasit berbahan methylene blue atau malachite green.',
 3, 'APPROVED');

-- ============================================================
-- Verifikasi data
-- ============================================================
SELECT 'users' AS tabel, COUNT(*) AS jumlah FROM users
UNION ALL
SELECT 'ikan',     COUNT(*) FROM ikan
UNION ALL
SELECT 'artikel',  COUNT(*) FROM artikel;
