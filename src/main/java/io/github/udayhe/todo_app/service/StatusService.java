package io.github.udayhe.todo_app.service;

import java.util.Set;

public interface StatusService {

    String getStatus();

    Boolean update(Set<String> ids);
}
