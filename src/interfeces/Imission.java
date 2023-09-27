package interfeces;

import dto.Mission;

import java.util.List;

public interface Imission {
    Mission Add(Mission mission);
    boolean Delete(String code);
    List<Mission> ShowList();
    List<Mission> MisiionHistory();
    List<Mission> MissionStatistic();
    Mission getCodeMission(String code);


}
