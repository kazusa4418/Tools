package util.manager;
import java.io.*;

@SuppressWarnings("unused")
public class FileManager {
    private File file;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public void setFile(File file) {
        if (this.file != null) {
            try {
                close();
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        this.file = file;
    }

    public void openOutputStream() throws IOException {
        if (this.file == null)
            throw new FileNotFoundException();
        inputStreamClose();
        if (fos == null)
            fos = new FileOutputStream(file);
        if (oos == null)
            oos = new ObjectOutputStream(fos);
    }

    public void openInputStream() throws IOException {
        if (this.file == null)
            throw new FileNotFoundException();
        outputStreamClose();
        if (fis == null)
            fis = new FileInputStream(file);
        if (ois == null)
            ois = new ObjectInputStream(fis);
    }

    public void writeByte(int b) throws IOException {
        if (fos == null)
            throw new StreamNotOpenException();
        fos.write(b);
    }

    public void writeByte(byte[] b) throws IOException {
        if (fos == null)
            throw new StreamNotOpenException();
        fos.write(b);
    }

    public int readByte() throws IOException {
        if (fis == null)
            throw new StreamNotOpenException();
        return fis.read();
    }

    public int readByte(byte[] b) throws IOException {
        if (fis == null)
            throw new StreamNotOpenException();
        return fis.read(b);
    }

    public void writeObject(Object o) throws IOException {
        if (oos == null)
            throw new StreamNotOpenException();
        oos.writeObject(o);
        oos.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        if (ois == null)
            throw new StreamNotOpenException();
        return ois.readObject();
    }

    public void close() throws IOException {
        file = null;
        inputStreamClose();
        outputStreamClose();
    }

    private void inputStreamClose() throws IOException {
        if (fis != null) {
            fis.close();
            fis = null;
        }
        if (ois != null) {
            ois.close();
            ois = null;
        }
    }

    private void outputStreamClose() throws IOException {
        if (fos != null) {
            fos.close();
            fos = null;
        }
        if (ois != null) {
            ois.close();
            ois = null;
        }
    }
}