package com.z0976190100.departments.controller.command;

public enum CommandEnum {

    SAVE {
        @Override
        public void execute() {

        }
    },
    GET {
        @Override
        public void execute() {

        }
    },
    GET_ALL {
        @Override
        public void execute() {

        }
    },
    UPDATE {
        @Override
        public void execute() {

        }
    },
    DELETE {
        @Override
        public void execute() {

        }
    };

    public abstract void execute();
}
