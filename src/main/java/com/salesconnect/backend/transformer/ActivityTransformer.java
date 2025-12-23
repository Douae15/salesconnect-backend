package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.ActivityDTO;
import com.salesconnect.backend.dto.ContactDTO;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Activity;
import com.salesconnect.backend.entity.Contact;
import com.salesconnect.backend.entity.User;

public class ActivityTransformer extends Transformer<Activity, ActivityDTO> {
    @Override
    public Activity toEntity(ActivityDTO activityDTO) {
        if (activityDTO == null)
            return null;
        else {
            Activity activity = new Activity();
            activity.setActivityId(activityDTO.getActivityId());
            activity.setDate(activityDTO.getDate());
            if (activityDTO.getUserId() != null) {
                User user = new User();
                user.setUserId(activityDTO.getUserId());
                activity.setUser(user);
            } else {
                activity.setUser(null);
            }
            activity.setType(activityDTO.getType());
            activity.setSummary(activityDTO.getSummary());
            if (activityDTO.getContactId() != null) {
                Contact contact = new Contact();
                contact.setContactId(activityDTO.getContactId());
                activity.setContact(contact);
            } else {
                activity.setContact(null);
            }
            return activity;
        }

    }

    @Override
    public ActivityDTO toDTO(Activity activity) {
        if (activity == null)
            return null;
        else {
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setActivityId(activity.getActivityId());
            activityDTO.setDate(activity.getDate());
            if (activity.getUser() != null) {
                activityDTO.setUserId(activity.getUser().getUserId());
            }
            activityDTO.setType(activity.getType());
            activityDTO.setSummary(activity.getSummary());
            if (activity.getContact() != null) {
                activityDTO.setContactId(activity.getContact().getContactId());
            }
            return activityDTO;
        }
    }
}
