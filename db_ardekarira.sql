-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 01 Jul 2025 pada 17.22
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_ardekarira`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `makanan`
--

CREATE TABLE `makanan` (
  `ID` int(11) NOT NULL,
  `Kode_Makanan` varchar(20) NOT NULL,
  `Nama_Makanan` varchar(30) NOT NULL,
  `Kategori` varchar(25) NOT NULL,
  `Harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `makanan`
--

INSERT INTO `makanan` (`ID`, `Kode_Makanan`, `Nama_Makanan`, `Kategori`, `Harga`) VALUES
(1, '001', 'Nasi Cokot Ayam', 'Makanan Berat', 7000),
(2, '002', 'Burito Kebab', 'Makanan Berat', 8000),
(3, '003', 'Mie Nyemek', 'Makanan Berat', 11000),
(4, '004', 'Lele Taliwang', 'Makanan Berat', 13000),
(5, '005', 'Nasi Goreng Padang', 'Makanan Berat', 12000),
(6, '006', 'Spagetti', 'Makanan Berat', 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `minuman`
--

CREATE TABLE `minuman` (
  `ID` int(11) NOT NULL,
  `Kode_Minuman` varchar(20) NOT NULL,
  `Nama_Minuman` varchar(50) NOT NULL,
  `Harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `minuman`
--

INSERT INTO `minuman` (`ID`, `Kode_Minuman`, `Nama_Minuman`, `Harga`) VALUES
(1, '101', 'Air Putih', 3000),
(2, '102', 'Es Teh', 4000),
(3, '103', 'Kopi', 5000),
(4, '104', 'Pop Ice - Coklat', 7000),
(5, '105', 'Pop Ice - Mangga', 7000),
(6, '106', 'Pop Ice - Strawberry', 7000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pegawai`
--

CREATE TABLE `pegawai` (
  `ID_Pegawai` int(11) NOT NULL,
  `Nama_Pegawai` varchar(30) NOT NULL,
  `Tempat_Tanggal_Lahir` varchar(30) NOT NULL,
  `Alamat` varchar(30) NOT NULL,
  `Jenis_Kelamin` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `No_HP` varchar(20) NOT NULL,
  `Jabatan` varchar(20) NOT NULL,
  `Gaji_Pokok` int(11) NOT NULL,
  `Status_Pegawai` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pegawai`
--

INSERT INTO `pegawai` (`ID_Pegawai`, `Nama_Pegawai`, `Tempat_Tanggal_Lahir`, `Alamat`, `Jenis_Kelamin`, `Email`, `No_HP`, `Jabatan`, `Gaji_Pokok`, `Status_Pegawai`) VALUES
(2, 'uu', '', '', '', '', '', 'Admin', 0, 'Aktif'),
(4, 'aa', '', '', '', '', '', 'HRD', 0, 'Aktif'),
(5, 'mm', '1', 'jj', 'Laki-Laki', '12', '4', 'HRD', 4800000, 'Aktif'),
(7, 'aa', '111111', '1111111111111111111', 'Laki-Laki', '1111111111111', '11111111111111', 'HRD', 0, 'Aktif');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `ID_Transaksi` int(11) NOT NULL,
  `Nama_Pemesan` varchar(100) NOT NULL,
  `Metode` varchar(20) NOT NULL,
  `Pesanan` text NOT NULL,
  `Total` int(11) NOT NULL,
  `Bayar` int(11) NOT NULL,
  `Kembalian` int(11) NOT NULL,
  `Tanggal` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`ID_Transaksi`, `Nama_Pemesan`, `Metode`, `Pesanan`, `Total`, `Bayar`, `Kembalian`, `Tanggal`) VALUES
(1, 'Devana Eka Wijaya', 'Cash', 'Burito Kebab + Pop Ice - Coklat (Rp 15000), Es Teh (Rp 4000), Mie Nyemek + Pop Ice - Strawberry (Rp 18000)', 37000, 50000, 13000, '2025-06-21 10:48:00'),
(2, 'Asura', 'Cash', 'Lele Taliwang + Es Teh (Rp 17000), Air Putih (Rp 3000)', 20000, 50000, 30000, '2025-06-21 10:53:53'),
(3, 'GGWP', 'GoPay', 'Nasi Goreng Padang + Pop Ice - Strawberry (Rp 19000)', 19000, 20000, 1000, '2025-06-21 10:54:08'),
(4, 'Dostakijo', 'Cash', 'Lele Taliwang + Pop Ice - Strawberry (Rp 20000), Kopi (Rp 5000)', 25000, 30000, 5000, '2025-06-21 10:54:19'),
(5, 'Kamilia Hoyi', 'Cash', 'Lele Taliwang + Pop Ice - Mangga (Rp 20000), Es Teh (Rp 4000)', 24000, 25000, 1000, '2025-06-22 00:18:15'),
(6, 'dos', 'Cash', 'Nasi Goreng Padang + Pop Ice - Coklat (Rp 19000), Pop Ice - Coklat (Rp 7000)', 26000, 50000, 24000, '2025-07-01 04:41:01');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(5000) NOT NULL,
  `Akses` varchar(50) NOT NULL,
  `Jabatan` varchar(20) DEFAULT '''Pegawai''',
  `Gaji` int(11) DEFAULT 0,
  `Status` varchar(20) DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`ID`, `Username`, `Password`, `Akses`, `Jabatan`, `Gaji`, `Status`) VALUES
(1, 'Bang Cepi', '2d1f54f703872ae72102cc8619683446dc5151d1d5c082cad76e753730ca45fc', 'Admin', 'Admin', 0, 'Aktif'),
(2, 'uu', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Admin', 'Admin', 0, 'Aktif'),
(3, 'aa', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Pegawai', 'HRD', 0, 'Aktif'),
(4, 'mm', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Pegawai', 'HRD', 4800000, 'Aktif'),
(5, 'bb', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Pegawai', 'Pegawai', 2000000, 'Aktif'),
(6, 'vv', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Pegawai', 'Pegawai', 0, 'Aktif');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users_request`
--

CREATE TABLE `users_request` (
  `ID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(3000) NOT NULL,
  `Akses` varchar(50) NOT NULL,
  `Status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `makanan`
--
ALTER TABLE `makanan`
  ADD PRIMARY KEY (`ID`);

--
-- Indeks untuk tabel `minuman`
--
ALTER TABLE `minuman`
  ADD PRIMARY KEY (`ID`);

--
-- Indeks untuk tabel `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`ID_Pegawai`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`ID_Transaksi`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- Indeks untuk tabel `users_request`
--
ALTER TABLE `users_request`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `makanan`
--
ALTER TABLE `makanan`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `minuman`
--
ALTER TABLE `minuman`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `pegawai`
--
ALTER TABLE `pegawai`
  MODIFY `ID_Pegawai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `ID_Transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `users_request`
--
ALTER TABLE `users_request`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
