package com.example;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.model.OneOfTripRequestsItems;
import com.example.model.StoredTrip;
import com.example.model.Trip;

@Service
public class SampleService {
	private Map<Long, StoredTrip> trips = new LinkedHashMap<>();
	private final AtomicLong counter = new AtomicLong();

	public StoredTrip createTrip(Trip trip) {
		StoredTrip storedTrip = new StoredTrip();

		try {
			BeanUtils.copyProperties(storedTrip, trip);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("Unable to store trip");
		}
		long newId = counter.incrementAndGet();
		storedTrip.setTripId(newId);
		trips.put(newId, storedTrip);
		return storedTrip;

	}

	public StoredTrip addRequest(long tripid, OneOfTripRequestsItems request) {

		StoredTrip trip = trips.get(tripid);
		if (trip == null) {
			throw new IllegalArgumentException("invalid trip id of " + tripid);
		} else {
			List<OneOfTripRequestsItems> requests = trip.getRequests();
			if (requests == null) {
				requests = new ArrayList<OneOfTripRequestsItems>();
				trip.setRequests(requests);
			}
			requests.add(request);
		}

		return trip;
	}

	public StoredTrip getTrip(long tripId) {
		StoredTrip trip = trips.get(tripId);
		if (trip == null) {
			throw new IllegalArgumentException("invalid trip id of " + tripId);
		}

		return trip;
	}

}
