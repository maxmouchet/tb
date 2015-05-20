package avis.structures;

public class MemberKey {
    private final String pseudo;

    public MemberKey(String pseudo) {
        this.pseudo = pseudo.trim().toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MemberKey) {
            MemberKey key = (MemberKey) obj;
            return this.pseudo.equals(key.pseudo);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 17 * pseudo.hashCode();
    }
}
