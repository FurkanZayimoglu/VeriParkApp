package com.example.veriparkapp.model.handshake;

public class Status {

        private boolean isSuccess;
        private  Error error;

        public boolean getIsSuccess() {
            return isSuccess;
        }

        public Error getError() {
            return error;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public void setError(Error errorObject) {
            this.error = errorObject;
        }
}
