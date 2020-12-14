/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Interest;
import org.springframework.samples.petclinic.model.NewUser;
import org.springframework.samples.petclinic.service.NewUserService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@RestController
public class NewUserController {

	private final NewUserService userService;

	@Autowired
	public NewUserController(NewUserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/newuser")
	public List<NewUser> getUser(@RequestParam(required = false) String username) {
	    if (username == null)
        {
            List<NewUser> list = userService.getAllUsers().stream().collect(Collectors.toList());
            return list;
        }
	    else
        {
            NewUser a = userService.findUser(username);
            if (a == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user");
            else
            {
                List<NewUser> list = new ArrayList<>();
                list.add(a);
                return list;
            }
        }
	}

	@PostMapping(value = "/newuser")
	public ResponseEntity postUser(@RequestBody NewUser user) {
	    for (Interest interest : user.getInterests())
        {
            interest.setUser(user);
        }
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
	}

}
