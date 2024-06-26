package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pojo.User;
import utility.Constants.UserType;
import utility.HashCode;

public class UserDoa {

	public User login(final Connection conn, final User user, final HashCode hashCode) {
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT * FROM USER WHERE username = ? AND password = ?")) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, hashCode.getHashCode(user.getPassword()));
			final ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setUserId(resultSet.getInt("userid"));

				final String userType = resultSet.getString("usertype");
				UserType type = null;
				if (userType.equals("ADMIN"))
					type = UserType.ADMIN;
				else if (userType.equals("USER"))
					type = UserType.USER;
				else if (userType.equals("CHEF"))
					type = UserType.CHEF;

				user.setUserType(type);
				user.setMessage("user found");
			} else {
				user.setMessage("Invalid credentails.");
				user.setOptType(-1);
			}
			resultSet.close();
		} catch (final Exception exception) {
			user.setMessage(exception.getMessage());
			user.setOptType(-1);
		}
		return user;
	}

	public User registration(final Connection conn, User user, final HashCode hashCode) {
		user = checkExistingUser(conn, user);

		if (user.getOptType() == -2) {
			return user;
		}

		int count;
		try (PreparedStatement preparedStatement = conn.prepareStatement(
				"INSERT INTO USER(username, firstname, lastname, password, usertype) " + "VALUES(?,?,?,?,?)")) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, hashCode.getHashCode(user.getPassword()));
			preparedStatement.setString(5, user.getUserType().toString());
			count = preparedStatement.executeUpdate();
			if (count == 1) {
				user.setMessage("Insert Successfully");
			} else {
				user.setMessage("Unable to insert.");
				user.setOptType(-2);
			}
		} catch (final Exception exception) {
			user.setMessage(exception.getMessage());
			user.setOptType(-2);
		}
		return user;
	}

	public User checkExistingUser(final Connection conn, final User user) {
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT COUNT(username) FROM user WHERE username = ?")) {
			preparedStatement.setString(1, user.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				final int count = resultSet.getInt(1);
				if (count >= 1) {
					user.setMessage("Username already exists");
					user.setOptType(-2);
				}
			}
		} catch (final Exception exception) {
			user.setMessage(exception.getMessage());
			user.setOptType(-2);
		}
		return user;
	}
}
