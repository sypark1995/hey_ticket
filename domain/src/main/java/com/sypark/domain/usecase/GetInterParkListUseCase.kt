package com.sypark.domain.usecase

import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import com.sypark.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetInterParkListUseCase(private val repository: MainRepository) {

    /**
     * invoke 를 사용하게 되면, 호출 부에서 해당 메소드 이름을 호출하지 않고, class 이름만으로 호출이 가능
     * 해당 프로젝트에서 사용된 useCase 의 경우 내부에서 많은 일을 하는 것이 아닌 repository 내부의 함수를 호출하는 역할만 하기 때문에
     * invoke 를 사용하여 호출 하는 것도 좋은 방법이라 생각한다.
     */

    suspend operator fun invoke(request: Request): Flow<List<OpenTicket>> {
        return repository.getInterParkOpenData(request)
    }
}