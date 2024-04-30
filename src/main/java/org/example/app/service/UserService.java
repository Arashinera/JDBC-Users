package org.example.app.service;

import org.example.app.entity.User;
import org.example.app.exceptions.UserException;
import org.example.app.repository.impl.UserRepository;
import org.example.app.utils.Constants;
import org.example.app.entity.ContactMapper;
import org.example.app.utils.ContactValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {

    UserRepository repository = new UserRepository();

    public String create(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException exception) {
                return exception.getErrors(errors);
            }
        }
        return repository.create(new ContactMapper().mapData(data));
    }

    public String read() {
        Optional<List<User>> optional = repository.read();

        if (optional.isPresent()) {
            List<User> list = optional.get();
            if (!list.isEmpty()) {
                AtomicInteger counter = new AtomicInteger(0);
                StringBuilder sb = new StringBuilder();
                list.forEach((user) ->
                        sb.append(counter.incrementAndGet())
                                .append(") ")
                                .append(user.toString())
                );

                return sb.toString();
            } else return Constants.DATA_ABSENT_MSG;
        } else return Constants.DATA_ABSENT_MSG;
    }

    public String update(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException exception) {
                return exception.getErrors(errors);
            }
        }

        return repository.update(new ContactMapper().mapData(data));
    }

    public String delete(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException exception) {
                return exception.getErrors(errors);
            }
        }

        return repository.delete(new ContactMapper().mapData(data).getId());
    }

    public String readById(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException exception) {
                return exception.getErrors(errors);
            }
        }

        Optional<User> optional =
                repository.readById(Long.parseLong(data.get("id")));

        if (optional.isPresent()) {
            User user = optional.get();

            return user.toString();
        } else return Constants.DATA_ABSENT_MSG;
    }
}
