package hiring.database.mybatis.mappers;

import hiring.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserSessionMapper {

    @Insert("INSERT INTO session (userid, token) VALUES(#{user.id}, #{token})")
    void openSession(@Param("user") User user, @Param("token") String token);

    @Delete("DELETE FROM session WHERE userid = #{user.id}")
    void closeSession(User user);
}
