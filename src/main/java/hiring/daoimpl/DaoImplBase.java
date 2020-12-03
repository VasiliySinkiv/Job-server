package hiring.daoimpl;

import hiring.database.mybatis.mappers.EmployeeMapper;
import hiring.database.mybatis.mappers.EmployerMapper;
import hiring.database.mybatis.mappers.SkillNameMapper;
import hiring.database.mybatis.mappers.UserSessionMapper;
import hiring.database.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected EmployeeMapper getEmployeeMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(EmployeeMapper.class);
    }

    protected EmployerMapper getEmployerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(EmployerMapper.class);
    }

    protected SkillNameMapper getSkillNameMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SkillNameMapper.class);
    }

    protected UserSessionMapper getUserSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserSessionMapper.class);
    }
}
