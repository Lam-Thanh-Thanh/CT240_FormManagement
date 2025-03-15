<template>
  <div>
    <h2>Rút gọn URL</h2>
    <input v-model="originalUrl" placeholder="Nhập link..." />
    <button @click="shortenUrl">Rút gọn</button>

    <div v-if="shortUrl">
      <p>Link rút gọn: <a :href="shortUrl" target="_blank">{{ shortUrl }}</a></p>
      <canvas ref="qrcodeCanvas"></canvas>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import QRCode from "qrcode";

export default {
  data() {
    return {
      originalUrl: "",
      shortUrl: "",
    };
  },
  methods: {
    async shortenUrl() {
      try {
        // Lấy formId từ originalUrl
        let formId = this.originalUrl.replace("http://localhost:5173/", "").replace("/", "");

        const response = await axios.post(`http://localhost:8080/api/url/shorten/${formId}`);
        
        if (response.data.shortUrl) {
          // Đảm bảo full URL khi hiển thị
          this.shortUrl = `http://localhost:5173/${response.data.shortUrl}`;

          // Tạo mã QR
          QRCode.toCanvas(this.$refs.qrcodeCanvas, this.shortUrl, (error) => {
            if (error) console.error(error);
          });
        }
      } catch (error) {
        console.error("Lỗi rút gọn URL", error);
      }
    },
  },
};
</script>
