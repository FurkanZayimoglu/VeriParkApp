package com.example.veriparkapp.model.handshake;

public class Error {


        private int code;
        private String message;

        public float getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
}
