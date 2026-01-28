package com.platformcommons.platform.service.blockprofile.domain.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

import com.platformcommons.platform.service.entity.common.*;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.blockprofile.domain.SchemeImplementationChallenge;
import com.platformcommons.platform.service.blockprofile.domain.*;

public interface SchemeImplementationChallengeRepository extends BaseRepository<SchemeImplementationChallenge, Long> {


}