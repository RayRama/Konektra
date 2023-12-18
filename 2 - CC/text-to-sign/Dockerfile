# Gunakan base image Python
FROM python:3.10-bullseye

# Set working directory di dalam container
WORKDIR /app

# Salin kode aplikasi dan file dependencies ke dalam container
COPY requirements.txt .
COPY main.py .

# Salin folder assets dan data ke dalam container
COPY assets /app/assets
COPY data /app/data

# Install dependencies menggunakan pip
RUN pip install --no-cache-dir -r requirements.txt

# Port yang perlu di-expose (sesuaikan dengan kebutuhan aplikasi Anda)
EXPOSE 8080

# Perintah untuk menjalankan aplikasi saat container dimulai
CMD ["python", "main.py"]
