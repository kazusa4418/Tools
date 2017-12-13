package util.file;
import java.io.*;

public class FileControl {
    private File file;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public FileControl(File file) throws IOException {
        this.file = file;
        fis = new FileInputStream(file);
        fos = new FileOutputStream(file);
        ois = new ObjectInputStream(fis);
        oos = new ObjectOutputStream(fos);
    }

    public void writeByte(int b) throws IOException {
        fos.write(b);
    }

    public void writeByte(byte[] b) throws IOException {
        fos.write(b);
    }

    public int readByte() throws IOException {
        return fis.read();
    }

    public int readByte(byte[] b) throws IOException {
        return fis.read(b);
    }

    public void writeObject(Object o) throws IOException {
        oos.writeObject(o);
        oos.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public void close() throws IOException {
        file = null;
        fis.close();
        fos.close();
        oos.close();
        ois.close();
    }
}