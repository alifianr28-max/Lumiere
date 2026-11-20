# Lumière

Aplikasi pribadi untuk tugas, kebiasaan (dengan Living Habit Icons), mood, jurnal, tujuan, dan pencapaian. Data tersimpan di perangkat, tanpa server.

## Isi repositori
- `www/` : aplikasi web (index.html, manifest PWA, service worker, ikon)
- `capacitor.config.json`, `package.json` : pembungkus Android
- `.github/workflows/build-apk.yml` : membangun APK otomatis di GitHub

## Cara mendapatkan APK (tanpa install apa pun di komputer)
1. Buat repositori baru di GitHub, lalu unggah semua isi folder ini (termasuk folder `.github`). Cara mudah: ekstrak ZIP, buka repositori, pilih **Add file > Upload files**, lalu seret semua isi.
2. Buka tab **Actions**. Workflow **Build APK** berjalan otomatis (sekitar 5 sampai 10 menit). Jika tidak, pilih workflow itu lalu **Run workflow**.
3. Setelah hijau, buka run tersebut, turun ke **Artifacts**, unduh **lumiere-debug-apk**, lalu ekstrak ZIP-nya untuk mendapat `app-debug.apk`.
4. Kirim ke ponsel Android, buka, dan izinkan **pasang dari sumber tidak dikenal**.
5. Di aplikasi: **Pengaturan > Notifikasi > Aktifkan**, lalu izinkan notifikasi.

Ini adalah APK debug untuk dipakai sendiri. Untuk Google Play kamu perlu APK atau AAB yang ditandatangani (buat keystore, lalu `./gradlew bundleRelease`).

## Widget layar utama
Setelah APK terpasang: tahan lama layar utama, pilih **Widget**, cari **Lumière**, lalu pilih salah satu dari tiga widget (Tugas hari ini, Penyelesaian dan level, Kebiasaan dan streak). Data widget diperbarui setiap kamu membuka atau mengubah data di aplikasi. Ketuk widget untuk membuka aplikasi.
Widget layar kunci hanya muncul jika perangkatmu mendukungnya (Android biasa pada ponsel umumnya tidak; sebagian tablet dan Android 15 pada tablet bisa). Kode widget ada di folder `native/` dan hanya bekerja di APK, bukan di versi web.

## Versi web (PWA)
Aktifkan **Settings > Pages** (branch `main`, folder `/www` lewat workflow Pages atau salin `www` ke root). Buka tautannya di Chrome, lalu pilih **Pasang aplikasi**. Notifikasi di versi web hanya muncul saat aplikasi terbuka.

## Tentang notifikasi
- **APK**: notifikasi dijadwalkan di perangkat untuk 14 hari ke depan (tugas berjam, pengingat kebiasaan, jurnal harian) dan muncul walau aplikasi tertutup. Buka aplikasi sesekali agar jadwal diperbarui.
- **Web**: hanya saat tab atau aplikasi terbuka. Push dari server (saat semuanya tertutup) butuh server VAPID dan tidak termasuk di sini.

## Batasan
- Font dimuat dari Google Fonts, jadi perlu internet pertama kali. Offline akan memakai font cadangan.
- Tidak ada login dan sinkronisasi antar perangkat. Gunakan **Pengaturan > Data dan bantuan** untuk cadangan.
- Bahasa Inggris baru sebagian. Asisten berbasis aturan, bukan AI generatif.
- iOS: gunakan versi web (PWA). APK hanya untuk Android.
