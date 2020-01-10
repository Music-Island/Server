package cn.itcast;

public class delEmailTemp extends Thread {
    String emailHost;
    delEmailTemp(String emailHost){
        this.emailHost = emailHost;
    }
    @Override
    public void run() {
        try {
            sleep(300000);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        EmailCheck.delEmailTemp(emailHost);
    }
}
