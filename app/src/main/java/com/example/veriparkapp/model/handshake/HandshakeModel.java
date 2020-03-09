package com.example.veriparkapp.model.handshake;


public class HandshakeModel {

        private String aesKey;
        private String aesIV;
        private String authorization;
        private String lifeTime;
        private Status status;


        // Getter Methods

        public String getAesKey() {
            return aesKey;
        }

        public String getAesIV() {
            return aesIV;
        }

        public String getAuthorization() {
            return authorization;
        }

        public String getLifeTime() {
            return lifeTime;
        }

        public Status getStatus() {
            return status;
        }

        // Setter Methods

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public void setAesIV(String aesIV) {
            this.aesIV = aesIV;
        }

        public void setAuthorization(String authorization) {
            this.authorization = authorization;
        }

        public void setLifeTime(String lifeTime) {
            this.lifeTime = lifeTime;
        }

        public void setStatus(Status statusObject) {
            this.status = statusObject;
        }
    }



