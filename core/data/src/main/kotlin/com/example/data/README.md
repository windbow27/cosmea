Mọi người đọc folder service để chọn các hàm cần dùng, khi dùng làm theo mẫu sau:
```kotlin
import com.example.data.service.UserService
import kotlinx.coroutines.launch
..
..
val userService = UserService(FirebaseFirestore.getInstance())
val userData = UserData("User1", "cheesedz", "123","1", 1,
            listOf("Server1", "Server2"), listOf("1", "2", "3"))
lifecycleScope.launch {
  userService.addUserData(userData)
  userService.getUserDataById("User1")
  userService.getUserDataByUsername("cheesedz")
  userService.updateUserData("User1", userData)
  userService.deleteUserDataById("User1")
}
```

1.Tạo service cần dùng
2.Tạo coroutine và gọi hàm cần dùng


