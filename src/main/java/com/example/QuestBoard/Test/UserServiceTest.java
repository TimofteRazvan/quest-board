package com.example.QuestBoard.Test;

import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Entity.UserDTO;
import com.example.QuestBoard.Repository.UserRepository;
import com.example.QuestBoard.Service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindAllUsers() {
    }
}
