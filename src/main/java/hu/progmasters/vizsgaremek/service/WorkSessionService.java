package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.WorkSession;
import hu.progmasters.vizsgaremek.dto.WorkSessionCreateCommand;
import hu.progmasters.vizsgaremek.dto.WorkSessionInfo;
import hu.progmasters.vizsgaremek.repository.WorkSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;
    private final ModelMapper modelMapper;

    public WorkSessionService(WorkSessionRepository workSessionRepository, ModelMapper modelMapper) {
        this.workSessionRepository = workSessionRepository;
        this.modelMapper = modelMapper;
    }

    public WorkSessionInfo saveWorkSession(WorkSessionCreateCommand command) {
        WorkSession workSession = new WorkSession();
        workSession.setEmployeeId(command.getEmployeeId());
        workSession.setProjectId(command.getProjectId());
        workSession.setBookedHours(command.getBookedHours());
        return modelMapper.map(workSessionRepository.saveWorkSession(workSession), WorkSessionInfo.class);
    }

    public List<WorkSessionInfo> findByEmployeeId(Integer employeeId) {
        List<WorkSession> workSessions = workSessionRepository.findByEmployeeId(employeeId);
        List<WorkSessionInfo> workSessionInfos = new ArrayList<>();
        for (WorkSession workSession : workSessions) {
            workSessionInfos.add(modelMapper.map(workSession,WorkSessionInfo.class));
        }
        return workSessionInfos;
    }



}
