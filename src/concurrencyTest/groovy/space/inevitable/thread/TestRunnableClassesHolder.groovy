package space.inevitable.thread


class TestRunnableClassesHolder {
    public static class TestRunnableA implements Runnable {
        private int count = 0;

        @Override
        void run() {
            this.count++;
        }

        public int getCount() {
            return count;
        }
    }

    public static class TestRunnableB implements Runnable {
        private int count = 0;

        @Override
        void run() {
            this.count++;
            Thread.sleep(50);
        }

        public int getCount() {
            return count;
        }
    }
}

