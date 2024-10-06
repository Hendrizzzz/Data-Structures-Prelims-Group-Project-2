package prelim.Objects;

import prelim.Exceptions.InvalidFileEntityNameException;

import java.util.Date;

public abstract class FileSystemEntity {
    private String name;
    private String path;
    private int size; // or double?
    private final Date creationDate;
    private Date modificationDate;

    public FileSystemEntity() {
        this.creationDate = new Date();
    }

    public FileSystemEntity(String name, Date creationDate) {
        if (name.contains("\\") || name.contains("."))
            throw new InvalidFileEntityNameException("File or Folder name can't have \"\\\" or \".\" in their names. ");
        this.name = name;
        this.creationDate = creationDate;
    }

    public FileSystemEntity(String name, String path, Date creationDate) {
        this.name = name;
        this.path = path;
        this.creationDate = creationDate;
    }


    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object e);

    @Override
    public abstract int hashCode();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("\\"))
            throw new InvalidFileEntityNameException("File or Folder name can't have \"\\\" in their names. ");
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    protected void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}