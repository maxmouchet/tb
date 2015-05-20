package avis.structures;

public class ItemKey {
    private final String klass;
    private final String title;

    public ItemKey(Class<?> klass, String title) {
        this.klass = klass.getSimpleName();
        this.title = title.trim().toLowerCase();
    }

    public String getKlass() {
        return this.klass;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemKey) {
            ItemKey key = (ItemKey) obj;
            return this.klass.equals(key.klass) && this.title.equals(key.title);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + klass.hashCode();
        hash = hash * 31 + title.hashCode();
        return hash;
    }
}
