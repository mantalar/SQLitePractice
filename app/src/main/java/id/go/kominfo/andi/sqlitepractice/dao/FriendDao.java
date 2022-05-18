package id.go.kominfo.andi.sqlitepractice.dao;

import java.util.List;

import id.go.kominfo.andi.sqlitepractice.model.Friend;

public interface FriendDao {
    void insert(Friend f);
    void update(Friend f);
    void delete(int id);
    Friend getAFriend(int id);
    List<Friend> getAllFriend();
}
