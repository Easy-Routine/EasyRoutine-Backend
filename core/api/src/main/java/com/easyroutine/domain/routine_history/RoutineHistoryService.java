package com.easyroutine.domain.routine_history;

import com.easyroutine.domain.routine_history.dto.RoutineHistoryDto;
import com.easyroutine.domain.routine_history.mapper.RoutineHistoryMapper;
import com.easyroutine.repository.routine_history.RoutineHistoryQueryRepository;
import com.easyroutine.repository.routine_history.RoutineHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoutineHistoryService {

	// repository
	private final RoutineHistoryRepository routineHistoryRepository;

	private final RoutineHistoryQueryRepository routineHistoryQueryRepository;

	// mapper
	private final RoutineHistoryMapper routineHistoryMapper;

	public RoutineHistoryService(RoutineHistoryRepository routineHistoryRepository,
			RoutineHistoryMapper routineHistoryMapper, RoutineHistoryQueryRepository routineHistoryQueryRepository) {
		this.routineHistoryRepository = routineHistoryRepository;
		this.routineHistoryMapper = routineHistoryMapper;
		this.routineHistoryQueryRepository = routineHistoryQueryRepository;
	}

	public int getExerciseTime(String date) {
		return routineHistoryQueryRepository.getExerciseTime(date);
	}

	public int getTotalVolume(String date) {
		return routineHistoryQueryRepository.getTotalVolume(date);
	}

	public List<RoutineHistory> getRoutineHistory(String date) {
		return routineHistoryQueryRepository.findAllByExerciseDate(date);
	}

	@Transactional(rollbackFor = Exception.class)
	public String createRoutineHistory(List<RoutineHistoryDto> routineHistoryDtos, String memberId) {
		List<RoutineHistory> routineHistories = routineHistoryDtos.stream()
			.map(dto -> routineHistoryMapper.toEntity(dto, memberId))
			.collect(Collectors.toList());
		routineHistoryRepository.saveAll(routineHistories);

		return "Success";
	}

	@Transactional(rollbackFor = Exception.class)
	public String updateRoutineHistory(Long id, List<RoutineHistoryDto> routineHistoryDtos, String memberId) {
		RoutineHistory routineHistory = routineHistoryQueryRepository.findAllByRoutineHistoryId(id)
			.orElseThrow(() -> new IllegalArgumentException("루틴 이력을 찾을 수 없습니다."));

		routineHistoryDtos.stream()
			.map(dto -> routineHistoryMapper.toEntity(dto, memberId))
			.forEach(entity -> routineHistory.updateRoutineHistory(entity));

		// TODO 변경감지로 update 처리
		routineHistoryRepository.save(routineHistory);

		return "Success";
	}

	@Transactional(rollbackFor = Exception.class)
	public String deleteRoutineHistory(Long id, String memberId) {
		RoutineHistory routineHistory = routineHistoryQueryRepository.findAllByRoutineHistoryId(id)
			.orElseThrow(() -> new IllegalArgumentException("루틴 이력을 찾을 수 없습니다."));

		// Soft delete 처리?
		routineHistoryRepository.delete(routineHistory);

		return "Success";
	}

}
