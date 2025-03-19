<template>
  <div class="upload-container">
    <input type="file" @change="handleFileUpload" />
    <button @click="uploadFile">Upload</button>
    <p v-if="message">{{ message }}</p>
  </div>
  <div>
    <h2>File PDF</h2>
    <iframe
      :src="backendUrl + this.message"
      width="100%"
      height="600px"
    ></iframe>
    <br />
    <a :href="backendUrl + this.message" download>Download PDF</a>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selectedFile: null,
      message: "",
      backendUrl: "http://localhost:8080/api/files/",
    };
  },
  methods: {
    handleFileUpload(event) {
      this.selectedFile = event.target.files[0];
    },
    async uploadFile() {
      if (!this.selectedFile) {
        this.message = "Please select a file!";
        return;
      }
      let formData = new FormData();
      formData.append("file", this.selectedFile);

      try {
        let response = await axios.post(
          "http://localhost:8080/api/files/upload",
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );
        this.message = response.data;
      } catch (error) {
        this.message = "Error uploading file!";
      }
    },
  },
};
</script>

<style>
.upload-container {
  margin: 20px;
  padding: 20px;
  border: 1px solid #ccc;
  text-align: center;
}
</style>
