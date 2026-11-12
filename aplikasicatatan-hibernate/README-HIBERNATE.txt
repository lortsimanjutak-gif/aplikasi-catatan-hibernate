APLIKASI CATATAN - VERSI HIBERNATE

Project ini sudah diubah dari JDBC langsung menjadi Hibernate ORM.

Persyaratan:
- JDK 17
- Apache NetBeans dengan dukungan Maven
- MySQL / XAMPP
- Database bernama: aplikasi_catatan

Konfigurasi database:
File: src/main/java/com/nasipecel/aplikasicatatan/hibernate/HibernateUtil.java

Default:
URL      jdbc:mysql://localhost:3306/aplikasi_catatan
Username root
Password (kosong)

Jika MySQL Anda memakai password, ubah properti hibernate.connection.password.

Struktur tabel yang dipakai:
users:
- id INT AUTO_INCREMENT PRIMARY KEY
- nama VARCHAR(150) NOT NULL
- email VARCHAR(190) NOT NULL UNIQUE
- password VARCHAR(255) NOT NULL

catatan:
- id INT AUTO_INCREMENT PRIMARY KEY
- user_id INT NOT NULL
- judul VARCHAR(255) NOT NULL
- isi TEXT
- favorit BOOLEAN NOT NULL DEFAULT FALSE
- FOREIGN KEY (user_id) REFERENCES users(id)

Jika database dan tabel belum ada, buat database terlebih dahulu:
CREATE DATABASE aplikasi_catatan;

Hibernate memakai hibernate.hbm2ddl.auto=update untuk membuat/menyesuaikan tabel entity.
Jika tabel lama Anda memiliki tipe/struktur kolom berbeda, backup database lalu sesuaikan skemanya terlebih dahulu.

Cara membuka:
1. Ekstrak ZIP.
2. NetBeans > File > Open Project.
3. Pilih folder aplikasicatatan-hibernate (folder yang berisi pom.xml).
4. Tunggu Maven mengunduh dependency.
5. Pastikan MySQL/XAMPP aktif dan database aplikasi_catatan tersedia.
6. Klik kanan project > Run.

Atau dari terminal di folder project:
mvn clean compile
mvn exec:java

Catatan:
- Tampilan dan fitur aplikasi Swing dipertahankan.
- Operasi database login, registrasi, tambah, daftar, cari, edit, dan hapus memakai Hibernate.
- Password tetap di-hash SHA-256 seperti program awal.
- Jangan membagikan password database asli jika Anda mengubah konfigurasi.
