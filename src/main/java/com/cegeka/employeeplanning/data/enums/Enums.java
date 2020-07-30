package com.cegeka.employeeplanning.data.enums;

public class Enums {
    public enum MitarbeiterStatus {
        ANGESTELLT(1),
        SUBUNTERNEHMER(2);

        private final int value;

        private MitarbeiterStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum MitarbeiterUnit {
        FACTORY_MUENCHEN(1),
        FACTORY_NUERNBERG(2);

        private final int value;

        private MitarbeiterUnit(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum EinsatzStatus {
        ANGEBOTEN(1),
        BEAUFTRAGT(2),
        ABGELEHNT(3);

        private final int value;

        private EinsatzStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
