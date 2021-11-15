package backend.demo.test.test.repository;

import org.apache.ibatis.annotations.Mapper;

import backend.demo.test.test.model.User;

@Mapper
public interface MapperRepository {

	User getOneUserById(int id);
}
