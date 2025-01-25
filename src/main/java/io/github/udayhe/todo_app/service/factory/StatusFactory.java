package io.github.udayhe.todo_app.service.factory;

import io.github.udayhe.todo_app.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StatusFactory {

    private final Map<String, StatusService> statusServices;

    @Autowired
    public StatusFactory(List<StatusService> statusServiceList) {
        Map<String, StatusService> tmpStatusServices = new HashMap<>();
        for (StatusService service : statusServiceList)
            tmpStatusServices.put(service.getStatus(), service);
        this.statusServices = Collections.unmodifiableMap(tmpStatusServices);
    }

    public StatusService get(String status) {
        return statusServices.get(status);
    }

}
