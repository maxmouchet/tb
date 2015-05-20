package avis.structures;

public class ReviewKey {
    private MemberKey memberKey;
    private ItemKey itemKey;

    public ReviewKey(MemberKey memberKey, ItemKey itemKey) {
        this.memberKey = memberKey;
        this.itemKey = itemKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReviewKey) {
            ReviewKey key = (ReviewKey) obj;
            return this.memberKey.equals(key.memberKey) && this.itemKey.equals(key.itemKey);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + memberKey.hashCode();
        hash = hash * 31 + itemKey.hashCode();
        return hash;
    }
}
