# Gunakan base image Python
FROM python:3.8

# Set working directory di dalam container
WORKDIR /app

# Salin kode aplikasi dan file dependencies ke dalam container
COPY requirements.txt .
COPY texttosign.py .

# Install dependencies menggunakan pip
RUN pip install --no-cache-dir -r requirements.txt

# Port yang perlu di-expose (sesuaikan dengan kebutuhan aplikasi Anda)
EXPOSE 5000

# Perintah untuk menjalankan aplikasi saat container dimulai
CMD ["python", "texttosign.py"]