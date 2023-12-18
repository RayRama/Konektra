## List command

Pastikan berada di folder yang sama dengan `Dockerfile`. Kemudian jalankan perintah berikut:

`bash
  docker build -t konektra-api:1.0 .
` Command untuk membuat image konektra-api dengan tag 1.0

---

Kemudian cek file `docker-compose.yml` untuk perintah lainnya.

`bash
  docker run -d -p 5000:5000 konektra-api:1.0
` Command untuk menjalankan image konektra-api dengan tag 1.0

`bash
  docker ps
` Command untuk melihat container yang sedang berjalan

`bash
  docker stop <container_id>
` Command untuk menghentikan container

`bash
  docker rm <container_id>
` Command untuk menghapus container

---

Untuk menjalankan docker compose, jalankan perintah berikut:

`bash
  docker-compose up -d atau docker compose up -d
` Command untuk menjalankan docker compose (-d untuk menjalankan di background)
