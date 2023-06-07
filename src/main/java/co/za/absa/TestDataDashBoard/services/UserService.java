package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.model.Users;
import co.za.absa.TestDataDashBoard.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UsersRepository repo;

    public ArrayList<Users> findUsers()
    {
        return (ArrayList<Users>) repo.findAll();
    }

    public int updateUsersStatus(String status, String username)
    {
       return repo.updatedUserStatus(status, username);
    }

}
