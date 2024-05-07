package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

import utility.Constants.UserType;

public class SessionDoa {
	public boolean addSession(final Connection conn, final int userId) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(
				"INSERT INTO usersession(userid, logintime, active) VALUES(?, CURRENT_TIMESTAMP, ?)")) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setBoolean(2, true);
			final int count = preparedStatement.executeUpdate();
			if (count >= 1) {
				return true;
			}
			return false;
		} catch (final Exception e) {
			return false;
		}
	}

	public boolean validateSession(final Connection conn, final int userId, final UserType userType) {
		System.out.println("validate session");
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT * FROM usersession WHERE userid = ? and active = true")) {
			preparedStatement.setInt(1, userId);
			final ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			final int sessionId = resultSet.getInt("sessionid");
			System.out.println("session id = " + sessionId);
			final Timestamp timestamp = resultSet.getTimestamp("logintime");

			final Instant currentSystemTime = Instant.now();

			final Instant loginTime = timestamp.toInstant();

			final Duration duration = Duration.between(loginTime, currentSystemTime);

			final long differenceInMinutes = duration.toMinutes();
			System.out.println(differenceInMinutes);

			if (userType.equals(UserType.CHEF)) {
				if (differenceInMinutes > 30) {
					updateSession(conn, userId);
					return false;
				}
			} else {
				if (differenceInMinutes > 5) {
					updateSession(conn, userId);
					return false;
				}
			}
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	public boolean updateSession(final Connection conn, final int userId) {
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("UPDATE usersession SET active = ? WHERE userid = ?")) {
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, userId);
			final int count = preparedStatement.executeUpdate();
			if (count >= 1) {
				return true;
			}
			return false;
		} catch (final Exception e) {
			return false;
		}
	}
}
